package org.example;

import org.example.db.DatabaseInitService;
import org.example.db.crud.ClientService;


public class Main {

    public static void main(String[] args) {
        // migrations
        DatabaseInitService.initDatabase();

        System.out.println("\nList ALL clients: ");
        System.out.println("ClientService.listAll(id) = " + ClientService.listAll());

        long id = ClientService.create("Umbrella Corporation");
        System.out.println("\nClient(Umbrella Corporation) created with id: " + id);

        System.out.println("\nGet client by id: " + id);
        System.out.println("ClientService.getById(id) = " + ClientService.getById(id));

        System.out.println("\nSet client name: Cyberdyne Systems");
        ClientService.setName(id,"Cyberdyne Systems");

        System.out.println("\nGet client by id: " + id);
        System.out.println("ClientService.getById(id) = " + ClientService.getById(id));

        System.out.println("\nDelete client by id: " + id);
        ClientService.deleteById(id);

        System.out.println("\nGet client by id: " + id);
        System.out.println("ClientService.getById(id) = " + ClientService.getById(id));

        System.out.println("\nList ALL clients: ");
        System.out.println("ClientService.listAll(id) = " + ClientService.listAll());

    }
}