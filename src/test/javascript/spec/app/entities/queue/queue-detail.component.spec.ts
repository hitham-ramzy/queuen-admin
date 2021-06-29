/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QueueNTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { QueueDetailComponent } from '../../../../../../main/webapp/app/entities/queue/queue-detail.component';
import { QueueService } from '../../../../../../main/webapp/app/entities/queue/queue.service';
import { Queue } from '../../../../../../main/webapp/app/entities/queue/queue.model';

describe('Component Tests', () => {

    describe('Queue Management Detail Component', () => {
        let comp: QueueDetailComponent;
        let fixture: ComponentFixture<QueueDetailComponent>;
        let service: QueueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QueueNTestModule],
                declarations: [QueueDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    QueueService,
                    JhiEventManager
                ]
            }).overrideTemplate(QueueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QueueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QueueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Queue(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.queue).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
