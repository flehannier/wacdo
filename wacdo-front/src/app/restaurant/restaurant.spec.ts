import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Restaurant } from './restaurant';

describe('Restaurant', () => {
  let component: Restaurant;
  let fixture: ComponentFixture<Restaurant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Restaurant]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Restaurant);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
