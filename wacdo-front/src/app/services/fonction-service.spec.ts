import { TestBed } from '@angular/core/testing';

import { FonctionService } from './fonction-service';

describe('FonctionService', () => {
  let service: FonctionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FonctionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
