import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CollaborateurModel } from '../../models/collaborateur.model';
import { CollaborateurService } from '../../services/collaborateur';
import { ActivatedRoute, Router } from '@angular/router';
import { Collaborateur } from '../collaborateur';
import { RestaurantModel } from '../../models/restaurant.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'collaborateur-form',
  imports: [FormsModule, CommonModule],
  templateUrl: './collaborateur-form.html',
  styleUrl: './collaborateur-form.css',
})
export class CollaborateurForm implements OnInit {
  public collaborateur!: CollaborateurModel;
  public restaurants!: RestaurantModel[];
  public restaurantId: number = -1;
  message?: string;
  isEditMode = false;
  dateEmbaucheISO: string = '';


  constructor(private collaborateurService: CollaborateurService,
    private router: Router,
    private route: ActivatedRoute){
  }

  ngOnInit()  {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.collaborateur = new CollaborateurModel();

    this.collaborateurService.listRestaurants().subscribe(item => {
      this.restaurants = item._embedded.restaurants;
    });

    if (id) {
      this.isEditMode = true;
      this.collaborateurService.editCollaborateur(id).subscribe(collab => {
      this.collaborateur = collab ?? new CollaborateurModel();  
      this.restaurantId = this.collaborateur.restaurant?.id ?? 0
    });
      
    return 
    }
  }
 

submit(): void {
 /*   const restaurant: RestaurantModel | undefined = this.collaborateurService.editRestaurent(Number(this.restaurantId))
      this.collaborateur.restaurant = restaurant;
    */  
    this.collaborateur.restaurant = this.restaurants.find( item => item.id == this.restaurantId)

    if (this.isEditMode) {
      this.collaborateurService.updateCollaborateur( this.collaborateur).subscribe( col => {;
          this.router.navigate(['collaborateurs']);
        });
    } else {
      this.collaborateurService.addCollaborateur(this.collaborateur).subscribe( col => {
         this.router.navigate(['collaborateurs']);
      });
    }
   
  }
}
