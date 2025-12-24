import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantUpdate } from './restaurant-update';

describe('RestaurantUpdate', () => {
  let component: RestaurantUpdate;
  let fixture: ComponentFixture<RestaurantUpdate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RestaurantUpdate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestaurantUpdate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
