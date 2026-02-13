import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CollaborateurService } from './collaborateur.service';
import { CollaborateurModel } from '../models/collaborateur-model';
import { FonctionModel } from '../models/fonction-model';
import { RestaurantModel } from '../models/restaurant-model';
import { RoleModel } from '../models/role-model';

describe('CollaborateurService', () => {
  let service: CollaborateurService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CollaborateurService]
    });
    service = TestBed.inject(CollaborateurService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('devrait être créé', () => expect(service).toBeTruthy());
});
