import { Component, HostListener, Input } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { FieldsFormTypeEnum, FormField, ModalAction, SelectOption } from '../../models/FieldsForm';
import { CollaborateurService } from '../../services/collaborateur-service';
import { CollaborateurModel } from '../../models/collaborateur-model';
import { GenericModalComponent } from '../generic-modal-component/generic-modal-component';
import { forkJoin } from 'rxjs';
import { FonctionService } from '../../services/fonction-service';
import { RestaurantService } from '../../services/restaurant-service';
import { Validators } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-navbar-component',
  imports: [RouterLink, RouterLinkActive, GenericModalComponent],
  templateUrl: './navbar-component.html',
  styleUrl: './navbar-component.css',
})
export class NavbarComponent {
  isMobileMenuOpen =  false;
  item?: CollaborateurModel;
  modalTitle: string = 'Collaborateur';
  formFields: FormField[] = []
  showModal = false;
  selectedItem: any = null;
  errors: string = '';
  showMenu = false;

   modalAction: ModalAction = 
  {
    label: 'Modifier',
    color: 'primary',
    callback: (data) => this.collaborateurService.save(data)
  };
  constructor(private fonctionService: FonctionService, private restaurantService: RestaurantService,private collaborateurService: CollaborateurService, private authService: AuthService, private router: Router){
  }

  loadProfil(){
    forkJoin({
      fonctions: this.fonctionService.listFonctions(),
      restaurants: this.restaurantService.listRestaurants()
    }).subscribe(({ fonctions, restaurants }) => {

      const optionsFonction: SelectOption[] = fonctions.map(f => ({ value: f.id, label: f.intitule }));
      const optionsRestaurant: SelectOption[] = restaurants.map(r => ({ value: r.id, label: r.nom }));

      this.formFields = [
        { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'prenom', label: 'Prénom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required]  },
        { key: 'email', label: 'Email',  type: FieldsFormTypeEnum.EMAIL, disabled: false, required: true, placeholder: '',  validators: [Validators.email, Validators.required] },
        { key: 'motDePasse', label: 'Mot de passe',  type: FieldsFormTypeEnum.PASSWORD, disabled: false, required: false, placeholder: '' },
        { key: 'fonction', label: 'Fonction', type: FieldsFormTypeEnum.SELECT, options: optionsFonction ,disabled: false, required: true, placeholder: 'Choix d\'une fonction',  validators: [Validators.required]  },
        { key: 'restaurant', label: 'Restaurant', type: FieldsFormTypeEnum.SELECT, options: optionsRestaurant, disabled: false, required: true, placeholder: 'Choix du restaurant',  validators: [Validators.required]  }
        ]
      });

    this.collaborateurService.getByUsername(this.authService.getUsername()).subscribe({
      next: (data: any) => {
        this.selectedItem = data;
      //  this.showModal = true;
      },
      error: (error: any) => {
        console.error('Erreur:', error);
        this.errors = error.error.message;
      }
    });
  }

  logout(){
    this.authService.logout();
    this.router.navigate(["/login"]);
  }

  closeModal() {
    this.showModal = false;
    this.selectedItem = null;
  }
  
  toggleMenu(){
    this.showMenu = !this.showMenu;
  }

  openMobileMenu() {
    this.isMobileMenuOpen = true;
    document.body.classList.add('overflow-hidden');
  }

  closeMobileMenu() {
    this.isMobileMenuOpen = false;
    document.body.classList.remove('overflow-hidden');
  }
}
