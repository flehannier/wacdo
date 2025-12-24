import { Component, OnInit } from '@angular/core';
import { CollaborateurService } from '../../../services/collaborateur';
import { CollaborateurModel } from '../../../models/collaborateur.model';
import { RestaurantModel } from '../../../models/restaurant.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SearchFilterPipe } from '../../../../search-filter-pipe';

@Component({
  selector: 'app-collaborateur-search',
  imports: [FormsModule, CommonModule, SearchFilterPipe],
  templateUrl: './collaborateur-search.html',
  styleUrl: './collaborateur-search.css',
})
export class CollaborateurSearch implements OnInit{
    public listCollaborateur? : CollaborateurModel[]
    public restaurants!: RestaurantModel[];
    public restaurantId!: number
    public nomCollaborateur!: string
    public searchTerm!: string;
  
  constructor(private collaborateurService : CollaborateurService,){
  }

  ngOnInit(): void {
    
     this.collaborateurService.listCollaborateur().subscribe(item => {
      this.listCollaborateur = item ?? []
    });

     this.collaborateurService.listRestaurants().subscribe(item => {
      this.restaurants = item._embedded.restaurants;
    });
  }

  onChange(){
   return this.collaborateurService.searchByRestaurant(this.restaurantId).subscribe(
    col => {
      this.listCollaborateur = col
    }
   )
  }

  searchByNom(){
    if(this.nomCollaborateur){
      return this.collaborateurService.searchByNom(this.nomCollaborateur).subscribe(
        col => {
          this.listCollaborateur = col
        })
    }
    return this.collaborateurService.listCollaborateur().subscribe(item => this.listCollaborateur = item ?? []);
  }

  onKeyUp(search: string){
    console.log(search)
      this.listCollaborateur = this.listCollaborateur?.filter(col => col.nom?.toLowerCase().includes(search.toLowerCase()));
    }
}
