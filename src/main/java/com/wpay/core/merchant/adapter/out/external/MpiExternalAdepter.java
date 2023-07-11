package com.wpay.core.merchant.adapter.out.external;

import com.wpay.core.merchant.application.port.out.external.SendHttpConnectionPort;
import com.wpay.core.merchant.global.annotation.ExternalAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class MpiExternalAdepter implements SendHttpConnectionPort {

}
