import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Covid19AppSharedModule } from 'app/shared/shared.module';
import { Covid19AppCoreModule } from 'app/core/core.module';
import { Covid19AppAppRoutingModule } from './app-routing.module';
import { Covid19AppHomeModule } from './home/home.module';
import { Covid19AppEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Covid19AppSharedModule,
    Covid19AppCoreModule,
    Covid19AppHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Covid19AppEntityModule,
    Covid19AppAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class Covid19AppAppModule {}
