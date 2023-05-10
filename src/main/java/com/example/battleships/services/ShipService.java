package com.example.battleships.services;

import com.example.battleships.models.Category;
import com.example.battleships.models.Ship;
import com.example.battleships.models.ShipType;
import com.example.battleships.models.User;
import com.example.battleships.models.dtos.CreateShipDTO;
import com.example.battleships.repositories.CategoryRepository;
import com.example.battleships.repositories.ShipRepository;
import com.example.battleships.repositories.UserRepository;
import com.example.battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipService {

    private ShipRepository shipRepository;
    private CategoryRepository categoryRepository;
    private final LoggedUser loggedUser;
    private UserRepository userRepository;

    public ShipService(ShipRepository shipRepository,
                       CategoryRepository categoryRepository,
                       LoggedUser loggedUser,
                       UserRepository userRepository) {
        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
    }

    public boolean create(CreateShipDTO createShipDTO){

        Optional<Ship> byName = this.shipRepository.findByName(createShipDTO.getName());

        if(byName.isPresent()){
            return false;
        }

        ShipType type = switch (createShipDTO.getCategory()){
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category category = this.categoryRepository.findByName(type);

        Optional<User> owner = this.userRepository.findById(this.loggedUser.getId());

        Ship ship = new Ship();

        ship.setName(createShipDTO.getName());
        ship.setPower(createShipDTO.getPower());
        ship.setHealth(createShipDTO.getHealth());
        ship.setCreated(createShipDTO.getCreated());
        ship.setCategory(category);
        ship.setUser(owner.get());

        this.shipRepository.save(ship);

        return true;
    }
}