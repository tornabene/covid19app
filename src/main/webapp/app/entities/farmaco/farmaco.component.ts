import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFarmaco } from 'app/shared/model/farmaco.model';
import { FarmacoService } from './farmaco.service';
import { FarmacoDeleteDialogComponent } from './farmaco-delete-dialog.component';

@Component({
  selector: 'jhi-farmaco',
  templateUrl: './farmaco.component.html'
})
export class FarmacoComponent implements OnInit, OnDestroy {
  farmacos?: IFarmaco[];
  eventSubscriber?: Subscription;

  constructor(protected farmacoService: FarmacoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.farmacoService.query().subscribe((res: HttpResponse<IFarmaco[]>) => (this.farmacos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFarmacos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFarmaco): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFarmacos(): void {
    this.eventSubscriber = this.eventManager.subscribe('farmacoListModification', () => this.loadAll());
  }

  delete(farmaco: IFarmaco): void {
    const modalRef = this.modalService.open(FarmacoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.farmaco = farmaco;
  }
}
