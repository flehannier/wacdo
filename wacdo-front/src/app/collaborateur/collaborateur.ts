import { Component, OnInit } from '@angular/core';
import { CollaborateurModel } from '../models/collaborateur.model';
import { DatePipe } from '@angular/common';
import { CollaborateurService } from '../services/collaborateur';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-collaborateur',
  imports: [DatePipe, RouterLink],
  templateUrl: './collaborateur.html',
  styleUrl: './collaborateur.css',
})
export class Collaborateur implements OnInit{
  listCollaborateur? : CollaborateurModel[]

  constructor(private collaborateurService : CollaborateurService, private router: Router){
  }

  loadData(){
    this.collaborateurService.listCollaborateur().subscribe(col  => {
      this.listCollaborateur = col;
    });
  }

  ngOnInit() {
    this.loadData()
  }
  
  deleteCollaborateur(collaborateur: CollaborateurModel)
  {
   this.collaborateurService.deleteCollaborateur(collaborateur)?.subscribe( col  => {
     this.loadData();
   })
  }
}
