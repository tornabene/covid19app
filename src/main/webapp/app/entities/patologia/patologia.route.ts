import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPatologia, Patologia } from 'app/shared/model/patologia.model';
import { PatologiaService } from './patologia.service';
import { PatologiaComponent } from './patologia.component';
import { PatologiaDetailComponent } from './patologia-detail.component';
import { PatologiaUpdateComponent } from './patologia-update.component';

@Injectable({ providedIn: 'root' })
export class PatologiaResolve implements Resolve<IPatologia> {
  constructor(private service: PatologiaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatologia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((patologia: HttpResponse<Patologia>) => {
          if (patologia.body) {
            return of(patologia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Patologia());
  }
}

export const patologiaRoute: Routes = [
  {
    path: '',
    component: PatologiaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.patologia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatologiaDetailComponent,
    resolve: {
      patologia: PatologiaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.patologia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatologiaUpdateComponent,
    resolve: {
      patologia: PatologiaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.patologia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatologiaUpdateComponent,
    resolve: {
      patologia: PatologiaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.patologia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
