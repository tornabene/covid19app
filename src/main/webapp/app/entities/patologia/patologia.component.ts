import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatologia } from 'app/shared/model/patologia.model';
import { PatologiaService } from './patologia.service';
import { PatologiaDeleteDialogComponent } from './patologia-delete-dialog.component';

@Component({
  selector: 'jhi-patologia',
  templateUrl: './patologia.component.html'
})
export class PatologiaComponent implements OnInit, OnDestroy {
  patologias?: IPatologia[];
  eventSubscriber?: Subscription;

  constructor(protected patologiaService: PatologiaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.patologiaService.query().subscribe((res: HttpResponse<IPatologia[]>) => (this.patologias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPatologias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPatologia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPatologias(): void {
    this.eventSubscriber = this.eventManager.subscribe('patologiaListModification', () => this.loadAll());
  }

  delete(patologia: IPatologia): void {
    const modalRef = this.modalService.open(PatologiaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patologia = patologia;
  }
}
