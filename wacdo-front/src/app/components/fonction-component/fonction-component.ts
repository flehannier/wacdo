import { Component } from '@angular/core';
import { GenericListComponent } from "../generic-list-component/generic-list-component";
import { FonctionModel } from '../../models/fonction-model';
import { FonctionService } from '../../services/fonction-service';
import { ListAction, ListColumn } from '../../models/list-model';

@Component({
  selector: 'app-fonction-component',
  imports: [GenericListComponent],
  templateUrl: './fonction-component.html',
  styleUrl: './fonction-component.css',
})
export class FonctionComponent {
  fonctions: FonctionModel[] = [];

  columns: ListColumn[] = [
    { key: 'id', label: 'Id', sortable: true },
    { key: 'intitule', label: 'Intitule', sortable: true }
  ];

  actions: ListAction[] = [
    {
      label: 'Modifier',
      color: 'primary',
      callback: (item) => this.onEdit(item)
    }
  ];

  constructor(private fonctionService: FonctionService){

  }

  ngOnInit(){
   this.fonctionService.listFonctions().subscribe({
      next: (data) => {
        this.fonctions = data
      },
      error: (error) => {
        console.error('Erreur:', error);
      }
    });
  }

  onAddFonction() {
    console.log('Ajouter un nouveau fonction');
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
