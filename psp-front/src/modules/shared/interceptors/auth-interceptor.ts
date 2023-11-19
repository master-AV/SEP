import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    request = request.clone({
      // withCredentials: true,
      setHeaders: {
        // "Access-Control-Allow-Credentials": "true",
        // "Access-Control-Allow-Origin": "*"
      }
    });
    return next.handle(request);
  }
}
