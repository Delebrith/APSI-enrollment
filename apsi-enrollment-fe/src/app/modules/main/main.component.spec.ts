import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';
import { AuthService } from 'src/app/core/auth/auth.service';
import { CurrentUserService } from 'src/app/core/auth/current-user.service';
import { MainComponent } from './main.component';

describe('MainComponent', () => {
  let component: MainComponent;
  let fixture: ComponentFixture<MainComponent>;

  const authService = jasmine.createSpyObj('AuthService', ['signOut']);
  const currentUserService = jasmine.createSpyObj('AuthAPIService', [], { currentUser$: of(null) });

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      declarations: [MainComponent],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: CurrentUserService, useValue: currentUserService },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
