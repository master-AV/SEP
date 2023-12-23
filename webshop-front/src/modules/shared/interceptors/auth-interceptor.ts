import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('intercept');
    request = request.clone({
      withCredentials: true,
      setHeaders: {
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Allow-Origin": "application/json"
      }
    });
    return next.handle(request);
  }
}
