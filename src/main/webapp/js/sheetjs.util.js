;(function(window, $) {
    var SheetJSUtil = {
        excel: function(link, tableId, sheetName) {
            var fileName = link.download || link.getAttribute('download') || sheetName + '.xlsx';
            var workbook = XLSX.utils.book_new();
            var worksheet = XLSX.utils.table_to_sheet(
                document.getElementById(tableId),
                { raw: true }
            );

            var merges = [];
            var tableEl = document.getElementById(tableId);

            for (var r = 0; r < tableEl.rows.length; r++) {
                var rowEl = tableEl.rows[r];
                var columnIndex = 0;

                for (var cIdx = 0; cIdx < rowEl.cells.length; cIdx++) {
                    var cellEl = rowEl.cells[cIdx];
                    var colspan = cellEl.colSpan || 1;
                    var rowspan = cellEl.rowSpan || 1;

                    if (colspan > 1 || rowspan > 1) {
                        merges.push({
                            s: { r: r, c: columnIndex },
                            e: { r: r + rowspan - 1, c: columnIndex + colspan - 1 }
                        });
                    }

                    var address = XLSX.utils.encode_cell({ r: r, c: columnIndex });
                    var cellObj = worksheet[address];
                    if (cellObj) {
                        var raw = cellObj.v + '';
                        var cleanVal = raw.replace(/,/g, '');
                        var isNum = /^-?\d+(\.\d+)?$/.test(cleanVal);
                        var styleObj = { font: { name: 'Liberation Sans', sz: 10 } };

                        if ($(cellEl).find('strong').length) styleObj.font.bold = true;

                        var alignVal = cellEl.getAttribute('align') || $(cellEl).css('text-align');
                        if (alignVal) {
                            styleObj.alignment = {
                                horizontal: alignVal.toLowerCase(),
                                vertical: 'center'
                            };
                        }

                        if (isNum) {
                            cellObj.t = 'n';
                            cellObj.v = parseFloat(cleanVal);
                            styleObj.numFmt = cleanVal.indexOf('.') > 0 ? '#,##0.00' : '0';
                        }

                        var borderObj = {};
                        if (cellEl.classList.contains('border-top'))    borderObj.top    = { style: 'thin', color: { auto: 1 } };
                        if (cellEl.classList.contains('border-bottom')) borderObj.bottom = { style: 'thin', color: { auto: 1 } };
                        if (cellEl.classList.contains('border-left'))   borderObj.left   = { style: 'thin', color: { auto: 1 } };
                        if (cellEl.classList.contains('border-right'))  borderObj.right  = { style: 'thin', color: { auto: 1 } };
                        if (Object.keys(borderObj).length) styleObj.border = borderObj;

                        cellObj.s = styleObj;
                    }

                    columnIndex += colspan;
                }
            }

            worksheet['!merges'] = merges;
            XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
            XLSX.writeFile(workbook, fileName);
            return false;
        }
    };

    window.SheetJS = SheetJSUtil;
})(window, jQuery);
