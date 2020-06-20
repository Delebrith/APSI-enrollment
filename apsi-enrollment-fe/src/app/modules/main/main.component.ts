import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/auth.service';
import { CurrentUserService } from 'src/app/core/auth/current-user.service';
import { User } from 'src/app/core/model/user.model';

@Component({
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  currentUser$: Observable<User>;

  constructor(private currentUserService: CurrentUserService, private authService: AuthService) {
    this.currentUser$ = currentUserService.currentUser$;
  }

  ngOnInit() {}

  onLogout() {
    this.authService.signOut().subscribe();
  }
}
