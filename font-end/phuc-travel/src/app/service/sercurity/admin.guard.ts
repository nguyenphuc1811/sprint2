import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {TokenService} from '../user/token.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private token: TokenService, private router: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.token.getToken()) {
      if (this.token.getRole() == 'ROLE_ADMIN' && this.token.isLogger()) {
        return true;
      } else {
        this.router.navigateByUrl('/')
        return false;
      }
    } else {
      this.router.navigateByUrl('/')
      return false;
    }
  }

}
