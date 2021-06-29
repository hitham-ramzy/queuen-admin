/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QueueNTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SuperAdminDetailComponent } from '../../../../../../main/webapp/app/entities/super-admin/super-admin-detail.component';
import { SuperAdminService } from '../../../../../../main/webapp/app/entities/super-admin/super-admin.service';
import { SuperAdmin } from '../../../../../../main/webapp/app/entities/super-admin/super-admin.model';

describe('Component Tests', () => {

    describe('SuperAdmin Management Detail Component', () => {
        let comp: SuperAdminDetailComponent;
        let fixture: ComponentFixture<SuperAdminDetailComponent>;
        let service: SuperAdminService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QueueNTestModule],
                declarations: [SuperAdminDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SuperAdminService,
                    JhiEventManager
                ]
            }).overrideTemplate(SuperAdminDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SuperAdminDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SuperAdminService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SuperAdmin(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.superAdmin).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
