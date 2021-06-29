import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BranchService } from './branch-service.model';
import { BranchServiceService } from './branch-service.service';

@Injectable()
export class BranchServicePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private branchServiceService: BranchServiceService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.branchServiceService.find(id).subscribe((branchService) => {
                    branchService.startingTime = this.datePipe
                        .transform(branchService.startingTime, 'yyyy-MM-ddTHH:mm:ss');
                    branchService.endingTime = this.datePipe
                        .transform(branchService.endingTime, 'yyyy-MM-ddTHH:mm:ss');
                    branchService.createdAt = this.datePipe
                        .transform(branchService.createdAt, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.branchServiceModalRef(component, branchService);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.branchServiceModalRef(component, new BranchService());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    branchServiceModalRef(component: Component, branchService: BranchService): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.branchService = branchService;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
