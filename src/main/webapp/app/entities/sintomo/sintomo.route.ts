import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISintomo, Sintomo } from 'app/shared/model/sintomo.model';
import { SintomoService } from './sintomo.service';
import { SintomoComponent } from './sintomo.component';
import { SintomoDetailComponent } from './sintomo-detail.component';
import { SintomoUpdateComponent } from './sintomo-update.component';

@Injectable({ providedIn: 'root' })
export class SintomoResolve implements Resolve<ISintomo> {
  constructor(private service: SintomoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISintomo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sintomo: HttpResponse<Sintomo>) => {
          if (sintomo.body) {
            return of(sintomo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sintomo());
  }
}

export const sintomoRoute: Routes = [
  {
    path: '',
    component: SintomoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.sintomo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SintomoDetailComponent,
    resolve: {
      sintomo: SintomoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.sintomo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SintomoUpdateComponent,
    resolve: {
      sintomo: SintomoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.sintomo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SintomoUpdateComponent,
    resolve: {
      sintomo: SintomoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.sintomo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
