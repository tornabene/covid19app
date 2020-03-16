import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISintomo } from 'app/shared/model/sintomo.model';
import { SintomoService } from './sintomo.service';
import { SintomoDeleteDialogComponent } from './sintomo-delete-dialog.component';

@Component({
  selector: 'jhi-sintomo',
  templateUrl: './sintomo.component.html'
})
export class SintomoComponent implements OnInit, OnDestroy {
  sintomos?: ISintomo[];
  eventSubscriber?: Subscription;

  constructor(protected sintomoService: SintomoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.sintomoService.query().subscribe((res: HttpResponse<ISintomo[]>) => (this.sintomos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSintomos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISintomo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSintomos(): void {
    this.eventSubscriber = this.eventManager.subscribe('sintomoListModification', () => this.loadAll());
  }

  delete(sintomo: ISintomo): void {
    const modalRef = this.modalService.open(SintomoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sintomo = sintomo;
  }
}
