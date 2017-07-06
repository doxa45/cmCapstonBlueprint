<#-- @ftlvariable name="self" type="com.coremedia.objectserver.view.ViewException" -->
<#-- @ftlvariable name="errorIcon" type="java.lang.String" -->
<#-- @ftlvariable name="compact" type="java.lang.Boolean" -->

<#escape x as x?html>
  <#assign boxId=bp.generateId("cm-error") />
  <#assign contentId=(self.bean!"")?keep_after_last('=')?replace(']', '') />
  <#assign template=(self.view!"")?keep_after_last("/")?replace(']', '') />

<div class="cm-error">
  <style type="text/css">
    .cm-error {
      font-family: Arial, Helvetica, sans-serif;
      position: relative;
      display: block;
      background: #eeeeee;
      font-size: 14px;
      border: 1px solid #cccccc;
      color: #000000;
      text-align: center;
    }
    .cm-error__info {
      display: inline-block;
      margin: 6px 3px;
      min-height: 24px;
      min-width: 24px;
      border: none;
      background: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAMAAADXqc3KAAAArlBMVEUAAAApgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLkpgLk5maqWAAAAOXRSTlMAAQIGCgsQEhMVGhsmOz1HVVZZW2ZnaXR8gIWGi4yPl5iam6KjpaiqsLK0t7nIytrc4OTm6ff5+/1S9qHIAAAA2ElEQVQYGWXBCTsCYRiG0btJG23Eq8bYQpmEJFPP//9j5ltGuTqHvfpJnSNJb1ZIKma9hEP9jSqbPn+SR5WmjUZjqtJDQlBbyDHA5OQ1vBt5Bpi8FKerYAJMFHQpfejYO3CmYPVZUuUUruTd4VwruoSFvBTHFOXwLe8exxStoVBkgCn6gS9FBpiiFTwrMsAUPcFQkQGmaAhNedvFABjkW3lNYCYnI8jkvFBqyUkJUjktnHOVMoJblYYEY0m75Zuz3EkaUxnp0AV7nVyV1w7/tEfzdbGej9pEv1p2SGPm5zNAAAAAAElFTkSuQmCC') no-repeat 6px center;
    }
    .cm-error__info span {
      display: inline-block;
      padding-left: 36px;
      line-height: 1;
    }
    .cm-error__info[onclick] {
      cursor: pointer;
    }
  </style>
  <div class="cm-error__info"<#if (cmpage.developerMode)!false> onclick="cmError.showBox('${boxId}'); return false;"</#if>>
      <span>An error occured in template <em>${template}</em>
        <#if contentId?has_content> for Content with ID <b>${contentId}</b>.</#if></span>
  </div>

  <#-- add popup box for developers and error to js console -->
  <#if (cmpage.developerMode)!false>
    <style>
      .cm-error__background {
        position: fixed;
        top: 0;
        left: 0;
        display: none;
        width: 100%;
        height: 100%;
        z-index: 9998;
        background-color: rgba(0, 0, 0, 0.5);
      }
      .cm-error__box {
        position: relative;
        width: 80vw;
        max-width: 800px;
        max-height: 95vh;
        margin: 50vh auto;
        transform: translateY(-50%);
        z-index: 9999;
        background: #fff;
        padding: 10px;
        border: 3px solid #800000;
        box-shadow: 0 0 20px #000;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 12px;
      }
      .cm-error__box button {
        position: absolute;
        top: 10px;
        right: 10px;
        z-index: 2;
      }
      .cm-error__details {
        position: relative;
      }
      .cm-error__details pre,
      .cm-error__details textarea {
        box-sizing: border-box;
        display: block;
        width: 100%;
        white-space: pre-wrap;
        word-break: normal;
        word-wrap: break-word;
        padding: 5px;
        margin: 0 0 10px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 12px;
        line-height: 1.17; /* 14px */
        color: #333333;
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 4px;
      }
      .cm-error__label {
        font-weight: bold;
        margin: 10px 0 5px 0;
      }
      .cm-error__small {
        max-height: 150px;
        overflow-y: scroll;
      }
    </style>
    <script>
      var cmError = (function (module) {
        if (typeof module.boxes === "undefined") {
          module.boxes = [];
        }
        module.registerBox = function (id) {
          module.boxes[module.boxes.length] = id;
        };
        module.showBox = function (id) {
          var modal = document.getElementById(id);
          // move modal div to end of dom to ensure that it will be rendered as overlay
          document.body.appendChild(modal);
          modal.style.display = "block";
          return false;
        };
        module.hideBox = function (id) {
          document.getElementById(id).style.display = "none";
        };
        module.hideAllBoxes = function () {
          for (var i = 0; i < module.boxes.length; i++) {
            module.hideBox(module.boxes[i]);
          }
        };
        return module;
      }(cmError || {}));

      // hide popup with ESC key
      document.onkeydown = function (e) {
        e = e || window.event;
        if (e.keyCode === 27) {
          cmError.hideAllBoxes();
        }
      };

      cmError.registerBox("${boxId}");

      // log error output to javascript console
      if (!window.console) {
        console = {
          warn: function () {
          }
        };
      }
      console.warn("CoreMedia Templating Error:\n<#noescape>${self.messagesAsList?keep_before("\n----")?js_string}</#noescape>");
    </script>
    <div id="${boxId}" class="cm-error__background">
      <div class="cm-error__box">
        <button onclick="cmError.hideBox('${boxId}');">Close</button>

        <div class="cm-error__details">
          <label class="cm-error__label">View:</label>
          <p>${self.view!""}</p>
          <label class="cm-error__label">Model:</label>
          <p>${self.bean!""}</p>
          <label class="cm-error__label">Cause:</label>
          <pre><#noescape>${self.messagesAsHtml!""}</#noescape></pre>
          <label class="cm-error__label">class hierarchy:</label>
          <pre class="cm-error__small"><#list self.getHierarchy() as hierachy>
            ${hierachy}
          </#list></pre>
          <label for="cm-error-text" class="cm-error__label">Full Stack Trace:</label>
          <textarea id="cm-error-text" readonly onclick="this.select();" class="cm-error__small" rows="10">${bp.getStackTraceAsString(self)!""}</textarea>
        </div>
      </div>
    </div>
  </#if>
</div>
</#escape>
