import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NavigationStart, Router, Event as NavigationEvent } from '@angular/router';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent implements OnInit {
  title = 'frontent';

  paginaadmin: boolean;

  constructor(private router:Router) {
    this.paginaadmin = false;
  }

  ngOnInit() {
    this.router.events
    .subscribe(
     (event: NavigationEvent) => {
       if(event instanceof NavigationStart) {
         if(window.location.href == environment.url + "/adminjuegos" ||
           window.location.href == environment.url + "/admincategorias" ||
           window.location.href == environment.url + "/adminusuarios") {
             this.paginaadmin = true;
         }
       }
     });
  }

  validarpageadmin(): boolean {
    this.paginaadmin = false;
    if(window.location.href == environment.url + "/adminjuegos" ||
     window.location.href == environment.url + "/admincategorias" ||
     window.location.href == environment.url + "4200/adminusuarios") {
      this.paginaadmin = true; 
    }
    return this.paginaadmin;
  }
}