import { Component } from '@angular/core';
import { GenericListComponent } from "../generic-list-component/generic-list-component";
import { ListAction, ListColumn } from '../../models/list-model';
import { AffectationModel } from '../../models/affectation-model';
import { AffectationService } from '../../services/affectation-service';

@Component({
  selector: 'app-affectation-component',
  imports: [GenericListComponent],
  templateUrl: './affectation-component.html',
  styleUrl: './affectation-component.css',
})
export class AffectationComponent {
  affectations: AffectationModel[] = [];

  columns: ListColumn[] = [
    { key: 'id', label: 'Id', sortable: true },
    { key: 'dateDebut', label: 'Date de début', sortable: true },
    { key: 'dateFin', label: 'Date de fin', sortable: true },
    { key: 'collaborateur.nom', label: 'Collaborateur', sortable: true, width: '250px' },
    { key: 'fonction.nom', label: 'Fonction', sortable: true },
    { key: 'restaurant.nom', label: 'Restaurant', sortable: true }
  ];

  actions: ListAction[] = [
    {
      label: 'Modifier',
      color: 'primary',
      callback: (item) => this.onEdit(item)
    },
    {
      label: 'Supprimer',
      color: 'danger',
      callback: (item) => this.onDelete(item)
    }
  ];

  constructor(private affectationService: AffectationService){

  }

  ngOnInit(){
    this.load();
  }

  load(){
    this.affectationService.listAffectations().subscribe({
      next: (data) => {
        this.affectations = data
      },
      error: (error) => {
        console.error('Erreur:', error);
      }
    });
  }

  onAddAffectation() {
    console.log('Ajouter un nouveau collaborateur');
  }

  onEdit(item: any) {
    console.log('Modifier:', item);
  }

  onDelete(item: AffectationModel) {
   this.affectationService.delete(item.id)?.subscribe({
      next: () => {
        this.load();
      },
      error: (error) => {
        console.error('Erreur:', error);
      }
   });
  }

  onSearchChanged(term: string) {
    console.log('Recherche:', term);
  }
}
