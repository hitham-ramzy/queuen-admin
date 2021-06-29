/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QueueNTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AgentDetailComponent } from '../../../../../../main/webapp/app/entities/agent/agent-detail.component';
import { AgentService } from '../../../../../../main/webapp/app/entities/agent/agent.service';
import { Agent } from '../../../../../../main/webapp/app/entities/agent/agent.model';

describe('Component Tests', () => {

    describe('Agent Management Detail Component', () => {
        let comp: AgentDetailComponent;
        let fixture: ComponentFixture<AgentDetailComponent>;
        let service: AgentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QueueNTestModule],
                declarations: [AgentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AgentService,
                    JhiEventManager
                ]
            }).overrideTemplate(AgentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Agent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.agent).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
