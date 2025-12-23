import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-binding',
  imports: [FormsModule],
  templateUrl: './binding.html',
  styles: ``,
})
export class Binding {
  titre : string = "test test"
  nom : string = "Frédéric"
  status: boolean = true
  changeTitre () {
    this.titre = "titre modifié"
  } 
}
