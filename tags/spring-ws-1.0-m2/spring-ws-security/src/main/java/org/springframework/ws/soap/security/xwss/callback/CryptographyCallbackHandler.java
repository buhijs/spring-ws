/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.soap.security.xwss.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.sun.xml.wss.impl.callback.CertificateValidationCallback;
import com.sun.xml.wss.impl.callback.DecryptionKeyCallback;
import com.sun.xml.wss.impl.callback.EncryptionKeyCallback;
import com.sun.xml.wss.impl.callback.SignatureKeyCallback;
import com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback;

/**
 * Default callback handler that handles cryptographic callback. This handler determines the exact callback passed, and
 * calls a template method for it. By default, all template methods throw an <code>UnsupportedCallbackException</code>,
 * so you only need to override those you need.
 *
 * @author Arjen Poutsma
 */
public class CryptographyCallbackHandler extends AbstractCallbackHandler {

    protected final void handleInternal(Callback callback) throws IOException, UnsupportedCallbackException {
        if (callback instanceof CertificateValidationCallback) {
            handleCertificateValidationCallback((CertificateValidationCallback) callback);
        }
        else if (callback instanceof DecryptionKeyCallback) {
            handleDecryptionKeyCallback((DecryptionKeyCallback) callback);
        }
        else if (callback instanceof EncryptionKeyCallback) {
            handleEncryptionKeyCallback((EncryptionKeyCallback) callback);
        }
        else if (callback instanceof SignatureKeyCallback) {
            handleSignatureKeyCallback((SignatureKeyCallback) callback);
        }
        else if (callback instanceof SignatureVerificationKeyCallback) {
            handleSignatureVerificationKeyCallback((SignatureVerificationKeyCallback) callback);
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }

    }

    //
    // Certificate validation
    //

    /**
     * Template method that handles <code>CertificateValidationCallback</code>s. Called from
     * <code>handleInternal()</code>. Default implementation throws an <code>UnsupportedCallbackException</code>.
     */
    protected void handleCertificateValidationCallback(CertificateValidationCallback callback)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    //
    // Decryption
    //

    /**
     * Method that handles <code>DecryptionKeyCallback</code>s. Called from <code>handleInternal()</code>. Default
     * implementation delegates to specific handling methods.
     *
     * @see #handlePrivateKeyRequest(com.sun.xml.wss.impl.callback.DecryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.DecryptionKeyCallback.PrivateKeyRequest)
     * @see #handleSymmetricKeyRequest(com.sun.xml.wss.impl.callback.DecryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.DecryptionKeyCallback.SymmetricKeyRequest)
     */
    protected final void handleDecryptionKeyCallback(DecryptionKeyCallback callback)
            throws IOException, UnsupportedCallbackException {
        if (callback.getRequest() instanceof DecryptionKeyCallback.PrivateKeyRequest) {
            handlePrivateKeyRequest(callback, (DecryptionKeyCallback.PrivateKeyRequest) callback.getRequest());
        }
        else if (callback.getRequest() instanceof DecryptionKeyCallback.SymmetricKeyRequest) {
            handleSymmetricKeyRequest(callback, (DecryptionKeyCallback.SymmetricKeyRequest) callback.getRequest());
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }
    }

    /**
     * Method that handles <code>DecryptionKeyCallback</code>s with <code>PrivateKeyRequest</code> . Called from
     * <code>handleDecryptionKeyCallback()</code>. Default implementation delegates to specific handling methods.
     *
     * @see #handlePublicKeyBasedPrivKeyCertRequest(com.sun.xml.wss.impl.callback.SignatureKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest)
     * @see #handleX509CertificateBasedRequest(com.sun.xml.wss.impl.callback.DecryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.DecryptionKeyCallback.X509CertificateBasedRequest)
     * @see #handleX509IssuerSerialBasedRequest(com.sun.xml.wss.impl.callback.DecryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.DecryptionKeyCallback.X509IssuerSerialBasedRequest)
     * @see #handleX509SubjectKeyIdentifierBasedRequest(com.sun.xml.wss.impl.callback.DecryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest)
     */
    protected final void handlePrivateKeyRequest(DecryptionKeyCallback callback,
                                                 DecryptionKeyCallback.PrivateKeyRequest request)
            throws IOException, UnsupportedCallbackException {
        if (request instanceof DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest) {
            handlePublicKeyBasedPrivKeyRequest(callback, (DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest) request);
        }
        else if (request instanceof DecryptionKeyCallback.X509CertificateBasedRequest) {
            handleX509CertificateBasedRequest(callback, (DecryptionKeyCallback.X509CertificateBasedRequest) request);
        }
        else if (request instanceof DecryptionKeyCallback.X509IssuerSerialBasedRequest) {
            handleX509IssuerSerialBasedRequest(callback, (DecryptionKeyCallback.X509IssuerSerialBasedRequest) request);
        }
        else if (request instanceof DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest) {
            handleX509SubjectKeyIdentifierBasedRequest(callback,
                    (DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest) request);
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }
    }

    /**
     * Template method that handles <code>DecryptionKeyCallback</code>s with <code>PublicKeyBasedPrivKeyRequest</code>s.
     * Called from <code>handlePrivateKeyRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handlePublicKeyBasedPrivKeyRequest(DecryptionKeyCallback callback,
                                                      DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>DecryptionKeyCallback</code>s with <code>X509CertificateBasedRequest</code>s.
     * Called from <code>handlePrivateKeyRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleX509CertificateBasedRequest(DecryptionKeyCallback callback,
                                                     DecryptionKeyCallback.X509CertificateBasedRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>DecryptionKeyCallback</code>s with <code>X509IssuerSerialBasedRequest</code>s.
     * Called from <code>handlePrivateKeyRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleX509IssuerSerialBasedRequest(DecryptionKeyCallback callback,
                                                      DecryptionKeyCallback.X509IssuerSerialBasedRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>DecryptionKeyCallback</code>s with <code>X509SubjectKeyIdentifierBasedRequest</code>s.
     * Called from <code>handlePrivateKeyRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleX509SubjectKeyIdentifierBasedRequest(DecryptionKeyCallback callback,
                                                              DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Method that handles <code>DecryptionKeyCallback</code>s with <code>SymmetricKeyRequest</code> . Called from
     * <code>handleDecryptionKeyCallback()</code>. Default implementation delegates to specific handling methods.
     *
     * @see #handleAliasSymmetricKeyRequest(com.sun.xml.wss.impl.callback.DecryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.DecryptionKeyCallback.AliasSymmetricKeyRequest)
     */
    protected final void handleSymmetricKeyRequest(DecryptionKeyCallback callback,
                                                   DecryptionKeyCallback.SymmetricKeyRequest request)
            throws IOException, UnsupportedCallbackException {
        if (request instanceof DecryptionKeyCallback.AliasSymmetricKeyRequest) {
            DecryptionKeyCallback.AliasSymmetricKeyRequest aliasSymmetricKeyRequest =
                    (DecryptionKeyCallback.AliasSymmetricKeyRequest) request;
            handleAliasSymmetricKeyRequest(callback, aliasSymmetricKeyRequest);
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }
    }

    /**
     * Template method that handles <code>DecryptionKeyCallback</code>s with <code>AliasSymmetricKeyRequest</code>s.
     * Called from <code>handleSymmetricKeyRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleAliasSymmetricKeyRequest(DecryptionKeyCallback callback,
                                                  DecryptionKeyCallback.AliasSymmetricKeyRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    //
    // Encryption
    //

    /**
     * Method that handles <code>EncryptionKeyCallback</code>s. Called from <code>handleInternal()</code>. Default
     * implementation delegates to specific handling methods.
     *
     * @see #handleSymmetricKeyRequest(com.sun.xml.wss.impl.callback.EncryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.EncryptionKeyCallback.SymmetricKeyRequest)
     * @see #handleX509CertificateRequest(com.sun.xml.wss.impl.callback.EncryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.EncryptionKeyCallback.X509CertificateRequest)
     */
    protected final void handleEncryptionKeyCallback(EncryptionKeyCallback callback)
            throws IOException, UnsupportedCallbackException {
        if (callback.getRequest() instanceof EncryptionKeyCallback.SymmetricKeyRequest) {
            handleSymmetricKeyRequest(callback, (EncryptionKeyCallback.SymmetricKeyRequest) callback.getRequest());
        }
        else if (callback.getRequest() instanceof EncryptionKeyCallback.X509CertificateRequest) {
            handleX509CertificateRequest(callback,
                    (EncryptionKeyCallback.X509CertificateRequest) callback.getRequest());
        }
        else {
            throw new UnsupportedCallbackException(callback);

        }
    }

    /**
     * Method that handles <code>EncryptionKeyCallback</code>s with <code>SymmetricKeyRequest</code> . Called from
     * <code>handleEncryptionKeyCallback()</code>. Default implementation delegates to specific handling methods.
     *
     * @see #handleAliasSymmetricKeyRequest(com.sun.xml.wss.impl.callback.EncryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.EncryptionKeyCallback.AliasSymmetricKeyRequest)
     */
    protected final void handleSymmetricKeyRequest(EncryptionKeyCallback callback,
                                                   EncryptionKeyCallback.SymmetricKeyRequest request)
            throws IOException, UnsupportedCallbackException {
        if (request instanceof EncryptionKeyCallback.AliasSymmetricKeyRequest) {
            handleAliasSymmetricKeyRequest(callback, (EncryptionKeyCallback.AliasSymmetricKeyRequest) request);
        }
    }

    /**
     * Template method that handles <code>EncryptionKeyCallback</code>s with <code>AliasSymmetricKeyRequest</code>s.
     * Called from <code>handleSymmetricKeyRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleAliasSymmetricKeyRequest(EncryptionKeyCallback callback,
                                                  EncryptionKeyCallback.AliasSymmetricKeyRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Method that handles <code>EncryptionKeyCallback</code>s with <code>X509CertificateRequest</code> . Called from
     * <code>handleEncryptionKeyCallback()</code>. Default implementation delegates to specific handling methods.
     *
     * @see #handleAliasX509CertificateRequest(com.sun.xml.wss.impl.callback.EncryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.EncryptionKeyCallback.AliasX509CertificateRequest)
     * @see #handleDefaultX509CertificateRequest(com.sun.xml.wss.impl.callback.EncryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.EncryptionKeyCallback.DefaultX509CertificateRequest)
     * @see #handlePublicKeyBasedRequest(com.sun.xml.wss.impl.callback.EncryptionKeyCallback,
     *      com.sun.xml.wss.impl.callback.EncryptionKeyCallback.PublicKeyBasedRequest)
     */
    protected final void handleX509CertificateRequest(EncryptionKeyCallback callback,
                                                      EncryptionKeyCallback.X509CertificateRequest request)
            throws IOException, UnsupportedCallbackException {
        if (request instanceof EncryptionKeyCallback.AliasX509CertificateRequest) {
            handleAliasX509CertificateRequest(callback, (EncryptionKeyCallback.AliasX509CertificateRequest) request);
        }
        else if (request instanceof EncryptionKeyCallback.DefaultX509CertificateRequest) {
            handleDefaultX509CertificateRequest(callback,
                    (EncryptionKeyCallback.DefaultX509CertificateRequest) request);
        }
        else if (request instanceof EncryptionKeyCallback.PublicKeyBasedRequest) {
            handlePublicKeyBasedRequest(callback, (EncryptionKeyCallback.PublicKeyBasedRequest) request);
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }
    }

    /**
     * Template method that handles <code>EncryptionKeyCallback</code>s with <code>AliasX509CertificateRequest</code>s.
     * Called from <code>handleX509CertificateRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleAliasX509CertificateRequest(EncryptionKeyCallback callback,
                                                     EncryptionKeyCallback.AliasX509CertificateRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>EncryptionKeyCallback</code>s with <code>DefaultX509CertificateRequest</code>s.
     * Called from <code>handleX509CertificateRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleDefaultX509CertificateRequest(EncryptionKeyCallback callback,
                                                       EncryptionKeyCallback.DefaultX509CertificateRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>EncryptionKeyCallback</code>s with <code>PublicKeyBasedRequest</code>s. Called
     * from <code>handleX509CertificateRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handlePublicKeyBasedRequest(EncryptionKeyCallback callback,
                                               EncryptionKeyCallback.PublicKeyBasedRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    //
    // Signing
    //

    /**
     * Method that handles <code>SignatureKeyCallback</code>s. Called from <code>handleInternal()</code>. Default
     * implementation delegates to specific handling methods.
     *
     * @see #handlePrivKeyCertRequest(com.sun.xml.wss.impl.callback.SignatureKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureKeyCallback.PrivKeyCertRequest)
     */
    protected final void handleSignatureKeyCallback(SignatureKeyCallback callback)
            throws IOException, UnsupportedCallbackException {
        if (callback.getRequest() instanceof SignatureKeyCallback.PrivKeyCertRequest) {
            handlePrivKeyCertRequest(callback, (SignatureKeyCallback.PrivKeyCertRequest) callback.getRequest());
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }
    }

    /**
     * Method that handles <code>SignatureKeyCallback</code>s with <code>PrivKeyCertRequest</code>s. Called from
     * <code>handleSignatureKeyCallback()</code>. Default implementation delegates to specific handling methods.
     *
     * @see #handleDefaultPrivKeyCertRequest(com.sun.xml.wss.impl.callback.SignatureKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureKeyCallback.DefaultPrivKeyCertRequest)
     * @see #handleAliasPrivKeyCertRequest(com.sun.xml.wss.impl.callback.SignatureKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureKeyCallback.AliasPrivKeyCertRequest)
     * @see #handlePublicKeyBasedPrivKeyCertRequest(com.sun.xml.wss.impl.callback.SignatureKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest)
     */
    protected final void handlePrivKeyCertRequest(SignatureKeyCallback cb,
                                                  SignatureKeyCallback.PrivKeyCertRequest request)
            throws IOException, UnsupportedCallbackException {
        if (request instanceof SignatureKeyCallback.DefaultPrivKeyCertRequest) {
            handleDefaultPrivKeyCertRequest(cb, (SignatureKeyCallback.DefaultPrivKeyCertRequest) request);
        }
        else if (cb.getRequest() instanceof SignatureKeyCallback.AliasPrivKeyCertRequest) {
            handleAliasPrivKeyCertRequest(cb, (SignatureKeyCallback.AliasPrivKeyCertRequest) request);
        }
        else if (cb.getRequest() instanceof SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest) {
            handlePublicKeyBasedPrivKeyCertRequest(cb, (SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest) request);
        }
        else {
            throw new UnsupportedCallbackException(cb);
        }
    }

    /**
     * Template method that handles <code>SignatureKeyCallback</code>s with <code>DefaultPrivKeyCertRequest</code>s.
     * Called from <code>handlePrivKeyCertRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleDefaultPrivKeyCertRequest(SignatureKeyCallback callback,
                                                   SignatureKeyCallback.DefaultPrivKeyCertRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>SignatureKeyCallback</code>s with <code>AliasPrivKeyCertRequest</code>s.
     * Called from <code>handlePrivKeyCertRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleAliasPrivKeyCertRequest(SignatureKeyCallback callback,
                                                 SignatureKeyCallback.AliasPrivKeyCertRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>SignatureKeyCallback</code>s with <code>PublicKeyBasedPrivKeyCertRequest</code>s.
     * Called from <code>handlePrivKeyCertRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handlePublicKeyBasedPrivKeyCertRequest(SignatureKeyCallback callback,
                                                          SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    //
    // Signature verification
    //

    /**
     * Method that handles <code>SignatureVerificationKeyCallback</code>s. Called from <code>handleInternal()</code>.
     * Default implementation delegates to specific handling methods.
     *
     * @see #handleX509CertificateRequest(com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback.X509CertificateRequest)
     */
    protected final void handleSignatureVerificationKeyCallback(SignatureVerificationKeyCallback callback)
            throws UnsupportedCallbackException, IOException {
        if (callback.getRequest() instanceof SignatureVerificationKeyCallback.X509CertificateRequest) {
            handleX509CertificateRequest(callback,
                    (SignatureVerificationKeyCallback.X509CertificateRequest) callback.getRequest());
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }
    }

    /**
     * Method that handles <code>SignatureVerificationKeyCallback</code>s with <code>X509CertificateRequest</code>s.
     * Called from <code>handleSignatureVerificationKeyCallback()</code>. Default implementation delegates to specific
     * handling methods.
     *
     * @see #handlePublicKeyBasedRequest(com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback.PublicKeyBasedRequest)
     * @see #handleX509IssuerSerialBasedRequest(com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest)
     * @see #handleX509SubjectKeyIdentifierBasedRequest(com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback,
     *      com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest)
     */
    protected final void handleX509CertificateRequest(SignatureVerificationKeyCallback callback,
                                                      SignatureVerificationKeyCallback.X509CertificateRequest request)
            throws UnsupportedCallbackException, IOException {
        if (request instanceof SignatureVerificationKeyCallback.PublicKeyBasedRequest) {
            handlePublicKeyBasedRequest(callback, (SignatureVerificationKeyCallback.PublicKeyBasedRequest) request);
        }
        else if (request instanceof SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest) {
            handleX509IssuerSerialBasedRequest(callback,
                    (SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest) request);
        }
        else if (request instanceof SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest) {
            handleX509SubjectKeyIdentifierBasedRequest(callback,
                    (SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest) request);
        }
        else {
            throw new UnsupportedCallbackException(callback);
        }
    }

    /**
     * Template method that handles <code>SignatureKeyCallback</code>s with <code>PublicKeyBasedPrivKeyCertRequest</code>s.
     * Called from <code>handlePrivKeyCertRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleX509SubjectKeyIdentifierBasedRequest(SignatureVerificationKeyCallback callback,
                                                              SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>SignatureKeyCallback</code>s with <code>X509IssuerSerialBasedRequest</code>s.
     * Called from <code>handlePrivKeyCertRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handleX509IssuerSerialBasedRequest(SignatureVerificationKeyCallback callback,
                                                      SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }

    /**
     * Template method that handles <code>SignatureKeyCallback</code>s with <code>PublicKeyBasedRequest</code>s. Called
     * from <code>handlePrivKeyCertRequest()</code>. Default implementation throws an
     * <code>UnsupportedCallbackException</code>.
     */
    protected void handlePublicKeyBasedRequest(SignatureVerificationKeyCallback callback,
                                               SignatureVerificationKeyCallback.PublicKeyBasedRequest request)
            throws IOException, UnsupportedCallbackException {
        throw new UnsupportedCallbackException(callback);
    }
}
