import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Window } from './window.model';
import { WindowService } from './window.service';

@Component({
    selector: 'jhi-window-detail',
    templateUrl: './window-detail.component.html'
})
export class WindowDetailComponent implements OnInit, OnDestroy {

    window: Window;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private windowService: WindowService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWindows();
    }

    load(id) {
        this.windowService.find(id).subscribe((window) => {
            this.window = window;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWindows() {
        this.eventSubscriber = this.eventManager.subscribe(
            'windowListModification',
            (response) => this.load(this.window.id)
        );
    }
}
