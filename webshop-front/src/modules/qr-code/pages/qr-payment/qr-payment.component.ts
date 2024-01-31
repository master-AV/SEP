import { Component } from '@angular/core';
import {QrService} from "../../services/qr.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-qr-payment',
  templateUrl: './qr-payment.component.html',
  styleUrls: ['./qr-payment.component.scss']
})
export class QrPaymentComponent {
  base64Image: string;// = '‰PNG  IHDRúú\bŽÍj\fÇIDATx^íÒQ®\\9Q£w6;Ÿõ, T@€HéÊ#çË ©ëWùçßÿþ§êøOUÿ¯zîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×C>8÷?þùÐ¾ï™Zæe-âÎIüžÝzîcß3µÌÊZÄ“ø=»õÜÇ¾gj™;”µˆ;\'ñ{vë¹}ÏÔ2w(kwNâ÷ìÖsûž©eîPÖ"îœÄïÙ­ç>ö=SËÜ¡¬EÜ9‰ß³ÛçÎÌ”}oqy ß2¸C¦ÅÌ”“oýÒsqy ß2¸C¦ÅÌ”“oýÒsqy ß2¸C¦ÅÌ”“oýÒsqy ß2¸C¦ÅÌ”“oýÒsqy ß2¸C¦ÅÌ”“oýÒsqy ß2¸C¦ÅÌ”“oýré¹3c˜fÈ´²\f±5Å¼•e\f³ÃÌn=÷ÓÊ2ÄÖóV–1Ì3»õÜL+Ë[SÌ[YÆ0;ÌìÖs_0­,ClM1oeÃì0³[Ï}Á´²\f±5Å¼•e\f³ÃÌn=÷ÓÊ2ÄÖóV–1Ì3»õÜî[Ä™–Éi™Œav˜Ù­ç®p‡Ø"¶È´L†LËd\f³ÃÌn=w…;Ä±E¦e2dZ&c˜fvë¹+Ü!¶ˆ-2-“!Ó2Ãì0³[Ï]á±El‘i™\f™–Éf‡™Ýzî wˆ-b‹LËdÈ´LÆ0;ÌìÖsW¸“1Ë&“1Ë&c˜fvë¹+ÜÉ˜e“É˜e“1Ì3»õÜîdÌ²ÉdÌ²Éf‡™Ýzî w2fÙd2fÙd\f³ÃÌn=w…;³l2³l2†Ùaf·ž»ÂŒY6™ŒY6Ãì0³Û¥ç>%{Ë´²\f±EûZ&3åä[¿ôÜL+Ë[´¯e2SN¾õKÏ}Á´²\f±EûZ&3åä[¿ôÜL+Ë[´¯e2SN¾õKÏ}Á´²\f±EûZ&3åä[¿ôÜL+Ë[´¯e2SN¾õËç~’ùž—3\'ñ{vë¹/¾çåÌIüžÝzî‹ïy9s¿g·žûâ{^ÎœÄïÙ­ç¾øž—3\'ñ{vë¹/¾çåÌIüžÝ>8÷Ûðg ¶È´L&Ãebë5=÷±C1-“Ép™ØzMÏ}ìPLËd2\\&¶^Ós;Ó2™\f—‰­×ôÜÇÅ´L&Ãebë5=÷±C1-“Ép™ØzÍçÎŸöµ˜¡©Ö>|LËd¦œ|ë—žûÂTk¾N¦e2SN¾õKÏ}aªµ_\'Ó2™)\'ßú¥ç¾0ÕÚ‡¯“i™Ì”“oýÒs_˜jíÃ×É´LfÊÉ·~é¹/LµöáëdZ&3åä[¿ü5çn˜“¡¬Eûv\fî[4Õ:¯ç¾ÈPÖ¢};wˆ-šj×s_d(kÑ¾ƒ;ÄMµÎë¹/2”µhßŽÁb‹¦ZçõÜÊZ´oÇà±ES­ózî‹\fe-Ú·cp‡Ø¢©ÖyWœ{–1Ì3Ä™–ÉPÖ2ÌòTæ=÷E†Ø"Ó2ÊZ†YžÊÜ ç¾È[dZ&CYË0ËS™ôÜb‹LËd(kfy*sƒžû"Cl‘i™\fe-Ã,OenÐs_dˆ-2-“¡¬e˜å©Ì >8wƒ>2-f¾Å/¤©–av˜!¶(kÍê¹Å/¤©–av˜!¶(kÍê¹Å/¤©–av˜!¶(kÍê¹Å/¤©–av˜!¶(kÍê¹Å/¤©–av˜!¶(kÍê¹Å/¤©–av˜!¶(kÍúàÜÍÛd\fîÐT‹Ø"Ób&ÃebkÊÉ·~é¹/–™!¶ˆ-2-f2\\&¶¦œ|ë—žûb™b‹Ø"Ób&ÃebkÊÉ·~é¹/–™!¶ˆ-2-f2\\&¶¦œ|ë—žûb™b‹Ø"Ób&ÃebkÊÉ·~é¹/–™!¶ˆ-2-f2\\&¶¦œ|ë—Î=3õÇâ™V–Ép™²Ö·øÍ»õÜL+Ëd¸LYë[üæÝzî¦•e2\\¦¬õ-~ón=÷ÓÊ2.SÖú¿y·žû‚ie™\f—)k}‹ß¼[Ï}Á´²L†Ë”µ¾ÅoÞíƒsç›Ø"¶2f™™\f—‰-bkŠyk_f·žûâ-“Ép™Ø"¶¦˜·öevë¹/Þ2™\f—‰-bkŠyk_f·žûâ-“Ép™Ø"¶¦˜·öevë¹/Þ2™\f—‰-bkŠyk_f·žûâ-“Ép™Ø"¶¦˜·öev»âÜ³\fíke™\f—3û–É¼e2»õÜU+Ëd¸œÙ·Læ-“Ù­ç®ZY&ÃåÌ¾e2o™Ìn=wÕÊ2.gö-“yËdvë¹«V–Ép9³o™Ì[&³[Ï]µ²L†Ë™}ËdÞ2™Ý®8wb+“-›3ÄÖ>æõ“™;õÜL‹bkóúÉÌzî¦Å\f±µyýdæN=÷Ób†ØÚÇ¼~2s§žû‚i1Clíc^?™¹SÏ}Á´˜!¶ö1¯ŸÌÜé/>÷©Ö¾epÇ0;Y†L‹bë¼žû¾epÇ0;Y†L‹bë¼žû¾epÇ0;Y†L‹bë¼žû¾epÇ0;Y†L‹bë¼žû¾epÇ0;Y†L‹bë¼žû¾epÇ0;Y†L‹bë¼KÏ¸c˜fˆ-b+c–³\f™3Äe­Y=÷E†Ø"¶2f9Ëi1ClQÖšÕs_dˆ-b+c–³\f™3Äe­Y=÷E†Ø"¶2f9Ëi1ClQÖšÕs_dˆ-b+c–³\f™3Äe­Y=÷E†Ø"¶2f9Ëi1ClQÖšué¹›3SÌ[Sšj‘i™ŒÁôÜÌ[Sšj‘i™ŒÁôÜÌ[Sšj‘i™ŒÁôÜÌ[Sšj‘i™ŒÁôÜÌ[Sšj‘i™ŒÁôÜÌ[Sšj‘i™ŒÁü5çžá2±El‘i1ClQÖú¿ù¼ž»z‹-2-fˆ-ÊZßâ7Ÿ×sWo±E¦Å\f±EYë[üæózîê-¶È´˜!¶(k}‹ß|^Ï]½Å™3Äe­oñ›Ïë¹«·Ø"Ób†Ø¢¬õ-~óyœûmøÃ[Ä™–Éd¸lp‡ØºSÏ}ìçd‹LËd2\\6¸ClÝ©ç>ös²E¦e2.Ü!¶îÔsû9Ù"Ó2™\f— î[wê¹ýœl‘i™L†Ëwˆ­;õÜÇ~N¶È´L&Ãeƒ;ÄÖ>8wþ±Nâ÷[†Ù1#Û1-“!¶îÔs_`Ë0;&cd;¦e2ÄÖzîlfÇdŒlÇ´L†ØºSÏ}-Ãì˜Œ‘í˜–É[wê¹/°e˜“1²Ó2bëN=÷¶\f³c2F¶cZ&ClÝéŠsgfJö–i1c˜,CY‹²¶ˆ­Ýzî¦ÅŒav²\fe-ÊvØ"¶vë¹/˜3†ÙÉ2”µ(Ûa‹ØÚ­ç¾`ZÌf\'ËPÖ¢l‡-bk·žû‚i1c˜,CY‹²¶ˆ­Ýzî¦ÅŒav²\fe-ÊvØ"¶v»ôÜ™1ÌÎT&Ãe2-f¾•}![»õÜÃL†ËdZÌ|+ûB¶vë¹‡™\f—É´˜ùVö…líÖs3.“i1ó­ìÙÚ­çf2\\&Óbæ[Ù²µ[Ï=Ìd¸L¦ÅÌ·²/dk·žûX†Ø:‰ßó­¾°ç>–!¶Nâ÷|ë†/ì¹eˆ­“ø=ßºá{îcbë$~Ï·nøÂžûX†Ø:‰ßó­¾°ç>–!¶Nâ÷|ë†/ì¹‡¸l˜}ƒ;”µˆ;»õÜC\\6ÌÎ¾ŒÁÊZÄÝzî!.fg_Æàe-âÎn=÷— ³³/cp‡²qg·ž{ˆË†ÙÙ—1¸CY‹¸³[Ï=ÄeÃììËÜ¡¬EÜÙíÒsŸbÞÊ2Ä–ÁšjÑT‹L‹™Ýzîa†Ø2¸CS-šj‘i1³[Ï=Ì[whªES-2-fvë¹‡bËàMµhªE¦ÅÌn=÷0ClÜ¡©MµÈ´˜Ù­çfˆ-ƒ;4Õ¢©™3»]qî\'eßÃV†ËÄV†Ë”µˆ;wê¹«ïa+Ãeb+ÃeÊZÄ;õÜÕ÷°•á2±•á2e-âÎzîê{ØÊp™ØÊp™²qçN=wõ=le¸Lle¸LY‹¸s§ž»ú¶2\\&¶2\\¦¬EÜ¹Óç^õ•ž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=¤ç^é¹×Czîõž{=äÊ²”àžÔƒIEND®B`‚';
  qrId: number = 0;

  constructor(private qrService: QrService,
            private route: ActivatedRoute,
              private router: Router) {
    console.log("CONSTRUCTOR")
    this.base64Image = localStorage.getItem('qrCode');
    // localStorage.clear();

    this.route.params.subscribe(
      params => {
        this.qrId = params['id'];
      });
  }

  payQrCode() {
    this.qrService.payWithQRCode(this.base64Image, this.qrId).subscribe(
      res=>{
        console.log(res)
        console.log(res.headers)
        console.log(res.headers.get('Location'))
        // localStorage.clear();
        window.location.href = res.headers.get('Location')
      },(error: HttpErrorResponse) => {
      // if (error.status === 404) {
        // Handle the 404 error here
        this.router.navigateByUrl('/qr/save/' + this.qrId);
        console.log('Error 404: Not Found');
      // } else {
      //   // Handle other HTTP errors
      //   console.error('An error occurred:', error.error);
      // }
    });
  }
}
