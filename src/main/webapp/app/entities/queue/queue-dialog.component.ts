import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Queue } from './queue.model';
import { QueuePopupService } from './queue-popup.service';
import { QueueService } from './queue.service';
import { BranchService, BranchServiceService } from '../branch-service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-queue-dialog',
    templateUrl: './queue-dialog.component.html'
})
export class QueueDialogComponent implements OnInit {

    queue: Queue;
    isSaving: boolean;

    branchservices: BranchService[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private queueService: QueueService,
        private branchServiceService: BranchServiceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.branchServiceService.query()
            .subscribe((res: ResponseWrapper) => { this.branchservices = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.queue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.queueService.update(this.queue));
        } else {
            this.subscribeToSaveResponse(
                this.queueService.create(this.queue));
        }
    }

    private subscribeToSaveResponse(result: Observable<Queue>) {
        result.subscribe((res: Queue) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Queue) {
        this.eventManager.broadcast({ name: 'queueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBranchServiceById(index: number, item: BranchService) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-queue-popup',
    template: ''
})
export class QueuePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private queuePopupService: QueuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.queuePopupService
                    .open(QueueDialogComponent as Component, params['id']);
            } else {
                this.queuePopupService
                    .open(QueueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
