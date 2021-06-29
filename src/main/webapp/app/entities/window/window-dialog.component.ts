import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Window } from './window.model';
import { WindowPopupService } from './window-popup.service';
import { WindowService } from './window.service';
import { Agent, AgentService } from '../agent';
import { BranchService, BranchServiceService } from '../branch-service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-window-dialog',
    templateUrl: './window-dialog.component.html'
})
export class WindowDialogComponent implements OnInit {

    window: Window;
    isSaving: boolean;

    currentagents: Agent[];

    branchservices: BranchService[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private windowService: WindowService,
        private agentService: AgentService,
        private branchServiceService: BranchServiceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.agentService
            .query({filter: 'window-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.window.currentAgent || !this.window.currentAgent.id) {
                    this.currentagents = res.json;
                } else {
                    this.agentService
                        .find(this.window.currentAgent.id)
                        .subscribe((subRes: Agent) => {
                            this.currentagents = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.branchServiceService.query()
            .subscribe((res: ResponseWrapper) => { this.branchservices = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.window.id !== undefined) {
            this.subscribeToSaveResponse(
                this.windowService.update(this.window));
        } else {
            this.subscribeToSaveResponse(
                this.windowService.create(this.window));
        }
    }

    private subscribeToSaveResponse(result: Observable<Window>) {
        result.subscribe((res: Window) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Window) {
        this.eventManager.broadcast({ name: 'windowListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAgentById(index: number, item: Agent) {
        return item.id;
    }

    trackBranchServiceById(index: number, item: BranchService) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-window-popup',
    template: ''
})
export class WindowPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private windowPopupService: WindowPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.windowPopupService
                    .open(WindowDialogComponent as Component, params['id']);
            } else {
                this.windowPopupService
                    .open(WindowDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
