import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveCcInformationComponent } from './save-cc-information.component';

describe('SaveCcInformationComponent', () => {
  let component: SaveCcInformationComponent;
  let fixture: ComponentFixture<SaveCcInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaveCcInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaveCcInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
