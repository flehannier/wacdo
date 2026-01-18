import { Component, OnInit } from '@angular/core';
import { GenericListComponent} from "../generic-list-component/generic-list-component";
import { CollaborateurList, CollaborateurModel } from '../../models/collaborateur-model';
import { CollaborateurService } from '../../services/collaborateur-service';
import { ListAction, ListColumn } from '../../models/list-model';

@Component({
  selector: 'app-collaborateur-component',
  imports: [GenericListComponent],
  templateUrl: './collaborateur-component.html',
  styleUrl: './collaborateur-component.css',
})
export class CollaborateurComponent implements OnInit{
  collaborateurs!: CollaborateurList[];

  columns: ListColumn[] = [
    { key: 'nom', label: 'Nom', sortable: true },
    { key: 'prenom', label: 'Prénom', sortable: true },
    { key: 'email', label: 'Email', sortable: true, width: '250px' },
    { key: 'fonction', label: 'Fonction', sortable: true },
    { key: 'restaurant', label: 'Restaurant', sortable: true }
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

  constructor(private collaborateurService: CollaborateurService){

  }

  ngOnInit(){
   this.collaborateurService.listCollaborateur().subscribe({
      next: (data) => {
        this.collaborateurs = data.map((collab: CollaborateurModel) => {
          // Prendre la dernière affectation
          const lastAffectation = collab.affectations?.[collab.affectations.length - 1];
          
          return {
            ...collab,
            fonction: lastAffectation?.fonction?.intitule || 'Non affecté',
            restaurant: lastAffectation?.restaurant?.nom || 'Non affecté'
          };
        });
      },
      error: (error) => {
        console.error('Erreur:', error);
        this.collaborateurs = [];
      }
    });
  }

  onAddCollaborateur() {
    console.log('Ajouter un nouveau collaborateur');
  }

  onEdit(item: any) {
    console.log('Modifier:', item);
  }

  onDelete(item: any) {
    console.log('Supprimer:', item);
  }

  onSearchChanged(term: string) {
    console.log('Recherche:', term);
  }
}
