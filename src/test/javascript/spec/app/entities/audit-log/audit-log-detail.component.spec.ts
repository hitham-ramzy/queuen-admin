/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QueueNTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuditLogDetailComponent } from '../../../../../../main/webapp/app/entities/audit-log/audit-log-detail.component';
import { AuditLogService } from '../../../../../../main/webapp/app/entities/audit-log/audit-log.service';
import { AuditLog } from '../../../../../../main/webapp/app/entities/audit-log/audit-log.model';

describe('Component Tests', () => {

    describe('AuditLog Management Detail Component', () => {
        let comp: AuditLogDetailComponent;
        let fixture: ComponentFixture<AuditLogDetailComponent>;
        let service: AuditLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QueueNTestModule],
                declarations: [AuditLogDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuditLogService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuditLogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuditLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuditLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuditLog(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auditLog).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
