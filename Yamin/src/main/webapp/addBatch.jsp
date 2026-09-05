<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yamin.repo.Repository,com.yamin.model.Admin,java.util.List" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Add Batch</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h2>Add Batch</h2> <a href="<%=request.getContextPath()%>/index.jsp">Home</a>
    <form method="post" action="batches">
      <label>Name: <input type="text" name="name" required></label>

      <label>Timing:
        <select name="timing" required>
          <optgroup label="Morning slots">
            <option value="06:00-07:00">06:00 - 07:00</option>
            <option value="07:00-08:00">07:00 - 08:00</option>
            <option value="08:00-09:00">08:00 - 09:00</option>
            <option value="09:00-10:00">09:00 - 10:00</option>
          </optgroup>
          <optgroup label="Evening slots">
            <option value="17:00-18:00">17:00 - 18:00</option>
            <option value="18:00-19:00">18:00 - 19:00</option>
            <option value="19:00-20:00">19:00 - 20:00</option>
            <option value="20:00-21:00">20:00 - 21:00</option>
          </optgroup>
          <option value="Other">Other (specify)</option>
        </select>
      </label>
      <!-- Hidden custom timing input shown when "Other" is selected -->
      <label id="otherTimingLabel" style="display:none;">Other timing: <input type="text" id="otherTimingInput" name="timingOther" placeholder="e.g. 15:30-16:30"></label>

      <button type="submit">Add Batch</button>
    </form>
    <%--<p><a class="btn" href="batches">Back to batches</a></p> --%>
  </div>
  <script>
    (function(){
      var sel = document.querySelector('select[name="timing"]');
      var otherLabel = document.getElementById('otherTimingLabel');
      var otherInput = document.getElementById('otherTimingInput');
      function updateVisibility(){
        if(!sel) return;
        if(sel.value === 'Other') otherLabel.style.display = 'block'; else otherLabel.style.display = 'none';
      }
      if(sel){
        sel.addEventListener('change', updateVisibility);
        // on submit, if Other selected and custom value provided, copy it into the select so server receives it
        sel.form.addEventListener('submit', function(e){
          if(sel.value === 'Other'){
            var v = (otherInput && otherInput.value) ? otherInput.value.trim() : '';
            if(v.length > 0){
              // create a hidden input to carry the custom timing instead of select
              var h = document.createElement('input');
              h.type = 'hidden'; h.name = 'timing'; h.value = v;
              sel.form.appendChild(h);
              // disable select so only custom value is used
              sel.disabled = true;
            } else {
              // prevent submit if Other selected but empty
              e.preventDefault();
              otherInput.focus();
              alert('Please enter the custom timing or choose a predefined slot.');
            }
          }
        });
        // initial visibility
        updateVisibility();
      }
    })();
  </script>
</body>
</html>
