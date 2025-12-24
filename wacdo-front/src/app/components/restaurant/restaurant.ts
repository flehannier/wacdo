import { Component, OnInit } from '@angular/core';
import { CollaborateurService } from '../../services/collaborateur';
import { RestaurantModel } from '../../models/restaurant.model';
import { RestaurantUpdate } from "./restaurant-update/restaurant-update";

@Component({
  selector: 'app-restaurant',
  standalone: true, 
  imports: [RestaurantUpdate],
  templateUrl: './restaurant.html',
  styleUrl: './restaurant.css',
})
export class Restaurant  implements OnInit{
  restaurants!: RestaurantModel[]
  restaurantSelectionne!: RestaurantModel;

  constructor(private collaborateurService : CollaborateurService){
  }

  ngOnInit(): void {
    this.collaborateurService.listRestaurants().subscribe(items =>  this.restaurants = items._embedded.restaurants)
  }

  onCollaborateurSelectionne(item: RestaurantModel){
    this.restaurantSelectionne = item;
  }

  onRestaurantUpdate(restaurant:RestaurantModel){
    console.log(restaurant)
    this.collaborateurService.addRestaurant(restaurant).subscribe( );
  }
}
