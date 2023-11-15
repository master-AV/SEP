import { Component } from '@angular/core'
import { Router } from '@angular/router'

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.css']
})
export class NotFoundComponent {
  constructor (private readonly router: Router) { }

  takeMeHome (): void {
    this.router.navigateByUrl('auth/registration')
  }
}
