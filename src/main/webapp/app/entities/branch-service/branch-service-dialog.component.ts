import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BranchService as BranchServiceModel } from './branch-service.model';
import { BranchServicePopupService } from './branch-service-popup.service';
import { BranchServiceService } from './branch-service.service';
import { Branch, BranchService } from '../branch';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-branch-service-dialog',
    templateUrl: './branch-service-dialog.component.html'
})
export class BranchServiceDialogComponent implements OnInit {

    branchServiceModel: BranchServiceModel;
    isSaving: boolean;

    branches: Branch[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private branchServiceService: BranchServiceService,
        private branchService: BranchService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.branchService.query()
            .subscribe((res: ResponseWrapper) => { this.branches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.branchServiceModel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.branchServiceService.update(this.branchServiceModel));
        } else {
            this.subscribeToSaveResponse(
                this.branchServiceService.create(this.branchServiceModel));
        }
    }

    private subscribeToSaveResponse(result: Observable<BranchServiceModel>) {
        result.subscribe((res: BranchServiceModel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BranchServiceModel) {
        this.eventManager.broadcast({ name: 'branchServiceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBranchById(index: number, item: Branch) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-branch-service-popup',
    template: ''
})
export class BranchServicePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private branchServicePopupService: BranchServicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.branchServicePopupService
                    .open(BranchServiceDialogComponent as Component, params['id']);
            } else {
                this.branchServicePopupService
                    .open(BranchServiceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
