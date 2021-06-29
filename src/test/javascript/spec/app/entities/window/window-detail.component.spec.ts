/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QueueNTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WindowDetailComponent } from '../../../../../../main/webapp/app/entities/window/window-detail.component';
import { WindowService } from '../../../../../../main/webapp/app/entities/window/window.service';
import { Window } from '../../../../../../main/webapp/app/entities/window/window.model';

describe('Component Tests', () => {

    describe('Window Management Detail Component', () => {
        let comp: WindowDetailComponent;
        let fixture: ComponentFixture<WindowDetailComponent>;
        let service: WindowService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QueueNTestModule],
                declarations: [WindowDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WindowService,
                    JhiEventManager
                ]
            }).overrideTemplate(WindowDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WindowDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WindowService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Window(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.window).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
