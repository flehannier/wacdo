import { Component, input, OnInit, Output, output, signal } from '@angular/core';
import { RestaurantModel } from '../../models/restaurant.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-restaurant-update',
  standalone: true, 
  imports: [FormsModule],
  templateUrl: './restaurant-update.html',
  styleUrl: './restaurant-update.css',
})
export class RestaurantUpdate implements OnInit{
  restaurant  = input<RestaurantModel>();
  restaurantUpdated = output<RestaurantModel>();

  ngOnInit(): void {
  }

  save(){
    this.restaurantUpdated.emit(this.restaurant()!);
  }
}
