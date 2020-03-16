import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaziente, Paziente } from 'app/shared/model/paziente.model';
import { PazienteService } from './paziente.service';
import { PazienteComponent } from './paziente.component';
import { PazienteDetailComponent } from './paziente-detail.component';
import { PazienteUpdateComponent } from './paziente-update.component';

@Injectable({ providedIn: 'root' })
export class PazienteResolve implements Resolve<IPaziente> {
  constructor(private service: PazienteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaziente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paziente: HttpResponse<Paziente>) => {
          if (paziente.body) {
            return of(paziente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Paziente());
  }
}

export const pazienteRoute: Routes = [
  {
    path: '',
    component: PazienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'covid19AppApp.paziente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PazienteDetailComponent,
    resolve: {
      paziente: PazienteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.paziente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PazienteUpdateComponent,
    resolve: {
      paziente: PazienteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.paziente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PazienteUpdateComponent,
    resolve: {
      paziente: PazienteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covid19AppApp.paziente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
