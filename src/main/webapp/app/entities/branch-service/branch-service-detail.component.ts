import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BranchService } from './branch-service.model';
import { BranchServiceService } from './branch-service.service';

@Component({
    selector: 'jhi-branch-service-detail',
    templateUrl: './branch-service-detail.component.html'
})
export class BranchServiceDetailComponent implements OnInit, OnDestroy {

    branchService: BranchService;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private branchServiceService: BranchServiceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBranchServices();
    }

    load(id) {
        this.branchServiceService.find(id).subscribe((branchService) => {
            this.branchService = branchService;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBranchServices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'branchServiceListModification',
            (response) => this.load(this.branchService.id)
        );
    }
}
