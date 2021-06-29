/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QueueNTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BranchServiceDetailComponent } from '../../../../../../main/webapp/app/entities/branch-service/branch-service-detail.component';
import { BranchServiceService } from '../../../../../../main/webapp/app/entities/branch-service/branch-service.service';
import { BranchService } from '../../../../../../main/webapp/app/entities/branch-service/branch-service.model';

describe('Component Tests', () => {

    describe('BranchService Management Detail Component', () => {
        let comp: BranchServiceDetailComponent;
        let fixture: ComponentFixture<BranchServiceDetailComponent>;
        let service: BranchServiceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QueueNTestModule],
                declarations: [BranchServiceDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BranchServiceService,
                    JhiEventManager
                ]
            }).overrideTemplate(BranchServiceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BranchServiceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BranchServiceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BranchService(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.branchService).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
