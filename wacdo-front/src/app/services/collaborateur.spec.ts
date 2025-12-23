import { TestBed } from '@angular/core/testing';

import { CollaborateurService } from './collaborateur';

describe('Collaborateur', () => {
  let service: CollaborateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollaborateurService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
