import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFarmaco, Farmaco } from 'app/shared/model/farmaco.model';
import { FarmacoService } from './farmaco.service';
import { FarmacoComponent } from './farmaco.component';
import { FarmacoDetailComponent } from './farmaco-detail.component';
import { FarmacoUpdateComponent } from './farmaco-update.component';

@Injectable({ providedIn: 'root' })
export class FarmacoResolve implements Resolve<IFarmaco> {
  constructor(private service: FarmacoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFarmaco> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((farmaco: HttpResponse<Farmaco>) => {
          if (farmaco.body) {
            return of(farmaco.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Farmaco());
  }
}

export const farmacoRoute: Routes = [
  {
    path: '',
    component: FarmacoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.farmaco.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FarmacoDetailComponent,
    resolve: {
      farmaco: FarmacoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.farmaco.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FarmacoUpdateComponent,
    resolve: {
      farmaco: FarmacoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.farmaco.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FarmacoUpdateComponent,
    resolve: {
      farmaco: FarmacoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.farmaco.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
