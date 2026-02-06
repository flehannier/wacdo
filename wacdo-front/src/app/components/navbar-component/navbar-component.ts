import { Component, ElementRef, HostListener, Input } from '@angular/core';
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
import { RoleService } from '../../services/role-service';

@Component({
  standalone: true,
  selector: 'app-navbar-component',
  imports: [RouterLink, RouterLinkActive],
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
  showAvatarMenu = false;

   modalAction: ModalAction = 
  {
    label: 'Modifier',
    color: 'primary',
    callback: (data) => this.collaborateurService.save(data)
  };
  constructor(private eRef: ElementRef, private roleService: RoleService,private fonctionService: FonctionService, private restaurantService: RestaurantService,private collaborateurService: CollaborateurService, private authService: AuthService, private router: Router){
  }

  logout(){
    this.authService.logout();
    this.router.navigate(["/login"]);
  }

  closeModal() {
    this.showModal = false;
    this.selectedItem = null;
  }
  
  toggleAvatarMenu() {
    this.showAvatarMenu = !this.showAvatarMenu;
  }

  // 🔹 Fermer le menu si clic en dehors
  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.showAvatarMenu = false;
    }
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
