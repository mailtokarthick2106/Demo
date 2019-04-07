import { Component } from '@angular/core';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isNoteView = true;

  constructor(private routesService: RouterService) { }

  switchToListView() {
    this.routesService.routeToEditCategory();
    this.isNoteView = false;
  }

  switchToNoteView() {
    this.routesService.routeToListView();
    this.isNoteView = true;
  }
}
