import { HttpClient, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { TokenStorageService } from './service/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'employee-management-system';

  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  /*constructor(private http: HttpClient) {}

  download(){
    this.http.get('http://localhost:4200/employee/export/pdf', {responseType: 'arrayBuffer'}).subscribe(pdf => 
    {
      const blob= new Blob([pdf], [type: 'application/pdf']);
      const filename="Employee.pdf";
    }, err => {
      console.log(err);

    })
  }*/
}
