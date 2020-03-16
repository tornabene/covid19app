import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'paziente',
        loadChildren: () => import('./paziente/paziente.module').then(m => m.Covid19AppPazienteModule)
      },
      {
        path: 'patologia',
        loadChildren: () => import('./patologia/patologia.module').then(m => m.Covid19AppPatologiaModule)
      },
      {
        path: 'farmaco',
        loadChildren: () => import('./farmaco/farmaco.module').then(m => m.Covid19AppFarmacoModule)
      },
      {
        path: 'sintomo',
        loadChildren: () => import('./sintomo/sintomo.module').then(m => m.Covid19AppSintomoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Covid19AppEntityModule {}
